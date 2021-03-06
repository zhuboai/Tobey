package org.mybatis.generator.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;


public class OraclePaginationPlugin extends PluginAdapter {
    @Override
    public boolean modelExampleClassGenerated(
        final TopLevelClass topLevelClass,
        final IntrospectedTable introspectedTable) {
        // add field, getter, setter for limit clause
        addPage(topLevelClass, introspectedTable, "page");

        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(final Document document,
        final IntrospectedTable introspectedTable) {
		/*
        final XmlElement parentElement = document.getRootElement();

        // 产生分页语句前半部分
        final XmlElement paginationPrefixElement = new XmlElement("sql");
        paginationPrefixElement.addAttribute(new Attribute("id",
                "Oracle_Pagination_Head"));

        final XmlElement pageStart = new XmlElement("if");
        pageStart.addAttribute(new Attribute("test", "page != null"));
        pageStart.addElement(new TextElement(
                "select * from ( select row_.*, rownum rownum_ from ( "));
        paginationPrefixElement.addElement(pageStart);
        parentElement.addElement(paginationPrefixElement);
		*/

        // 产生分页语句后半部分
		/*
        final XmlElement paginationSuffixElement = new XmlElement("sql");
        paginationSuffixElement.addAttribute(new Attribute("id",
                "Oracle_Pagination_Tail"));

        final XmlElement pageEnd = new XmlElement("if");
        pageEnd.addAttribute(new Attribute("test", "page != null"));
        pageEnd.addElement(new TextElement(
                "<![CDATA[ ) row_ ) where rownum_ > #{page.begin} and rownum_ <= #{page.end} ]]>"));
        paginationSuffixElement.addElement(pageEnd);
        parentElement.addElement(paginationSuffixElement);
		*/
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(
        final XmlElement element, final IntrospectedTable introspectedTable) {
		/*
        final XmlElement pageStart = new XmlElement("include"); //$NON-NLS-1$
        pageStart.addAttribute(new Attribute("refid", "Oracle_Pagination_Head"));
        element.getElements().add(0, pageStart);

        final XmlElement isNotNullElement = new XmlElement("include"); //$NON-NLS-1$
        isNotNullElement.addAttribute(new Attribute("refid",
                "Oracle_Pagination_Tail"));
        element.getElements().add(isNotNullElement);
		*/
        return super.sqlMapUpdateByExampleWithoutBLOBsElementGenerated(element,
            introspectedTable);
    }

    /**
     * @param topLevelClass
     * @param introspectedTable
     * @param name
     */
    private void addPage(final TopLevelClass topLevelClass,
        final IntrospectedTable introspectedTable, final String name) {
        topLevelClass.addImportedType(new FullyQualifiedJavaType(
                "com.ailk.sysframe.dao.components.Page"));

        final CommentGenerator commentGenerator = context.getCommentGenerator();
        final Field field = new Field();
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setType(new FullyQualifiedJavaType(
                "com.ailk.sysframe.dao.components.Page"));
        field.setName(name);
        commentGenerator.addFieldComment(field, introspectedTable);
        topLevelClass.addField(field);

        final char c = name.charAt(0);
        final String camel = Character.toUpperCase(c) + name.substring(1);
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName("set" + camel);
        method.addParameter(new Parameter(
                new FullyQualifiedJavaType(
                    "com.ailk.sysframe.dao.components.Page"), name));
        method.addBodyLine("this." + name + "=" + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
        method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(new FullyQualifiedJavaType(
                "com.ailk.sysframe.dao.components.Page"));
        method.setName("get" + camel);
        method.addBodyLine("return " + name + ";");
        commentGenerator.addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    /**
     * This plugin is always valid - no properties are required
     */
    @Override
    public boolean validate(final List<String> warnings) {
        return true;
    }
}
