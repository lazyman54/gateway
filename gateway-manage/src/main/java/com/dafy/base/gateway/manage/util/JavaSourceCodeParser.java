package com.dafy.base.gateway.manage.util;

import com.dafy.base.gateway.common.domain.enums.TransmitProtocol;
import com.dafy.base.gateway.manage.domain.ParamDefinitionVo;
import com.dafy.base.gateway.manage.domain.TransmitMapVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.JavaType;
import org.jboss.forge.roaster.model.JavaUnit;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lazyman
 * @version v1.0
 * @date 2017/9/6
 */
@RequiredArgsConstructor
public class JavaSourceCodeParser {

    private final static Pattern PATTERN = Pattern.compile("([A-Z])");

    private final String javaCode;

    private String fullPath;

    public List<TransmitMapVO> parseJavaCode() {

        JavaUnit javaUnit = Roaster.parseUnit(javaCode);

        JavaType<?> type = javaUnit.getGoverningType();

        if (!type.isInterface()) {
            throw new IllegalArgumentException("请上传接口谢谢！");
        }

        List<TransmitMapVO> transmitMapVOS = new ArrayList<>();

        JavaInterfaceSource parseInterface = (JavaInterfaceSource) type;

        this.fullPath = parseInterface.getQualifiedName();

        List<MethodSource<JavaInterfaceSource>> methods = parseInterface.getMethods();

        for (MethodSource<JavaInterfaceSource> method : methods) {

            TransmitMapVO transmit = parseFromMethod(method);
            transmitMapVOS.add(transmit);
        }
        return transmitMapVOS;

    }

    private TransmitMapVO parseFromMethod(MethodSource<JavaInterfaceSource> method) {

        TransmitMapVO transmitMapVO = new TransmitMapVO();
        transmitMapVO.setMethodName(method.getName());
        transmitMapVO.setInvokeClassName(fullPath);
        transmitMapVO.setProtocol(TransmitProtocol.DUBBO);
        transmitMapVO.setSync("true");
        transmitMapVO.setLevel("MIDDLE");
        transmitMapVO.setUrlAdapter("false");
        transmitMapVO.setHttpUrl(parseFromMethodName(method.getName()));


        List<ParameterSource<JavaInterfaceSource>> parameters = method.getParameters();

        List<ParamDefinitionVo> paramDefinitionList = new ArrayList<>(parameters.size());

        for (ParameterSource<JavaInterfaceSource> parameter : parameters) {
            ParamDefinitionVo definition = parseFromParam(parameter);
            paramDefinitionList.add(definition);
        }

        transmitMapVO.setParamDefinitions(paramDefinitionList);
        return transmitMapVO;
    }

    private String parseFromMethodName(String name) {
        Matcher matcher = PATTERN.matcher(name);
        StringBuffer sb = new StringBuffer("/");
        while (matcher.find()) {
            matcher.appendReplacement(sb, "/" + matcher.group(1).toLowerCase());
        }
        matcher.appendTail(sb);
        //sb.insert(0, "/");
        return sb.toString();
    }

    private ParamDefinitionVo parseFromParam(ParameterSource<JavaInterfaceSource> parameter) {

        ParamDefinitionVo paramDefinition = new ParamDefinitionVo();
        paramDefinition.setParamType(parameter.getType().getQualifiedNameWithGenerics());
        paramDefinition.setParamName(parameter.getName());
        paramDefinition.setArgs(String.valueOf(parameter.isVarArgs()));
        paramDefinition.setFromUrl("false");
        paramDefinition.setNecessary("false");

        paramDefinition.setBaseType(String.valueOf(isPrimitive(parameter.getType())));

        return paramDefinition;

    }

    private boolean isPrimitive(Type paramType) {
        if (paramType.isPrimitive()) {
            return true;
        }

        String paramClass = paramType.getQualifiedName();

        if (StringUtils.equals(String.class.getName(), paramClass)) {
            return true;
        }

        try {
            return ((Class) Class.forName(paramType.getQualifiedName()).getField("TYPE").get(null)).isPrimitive();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }
}
