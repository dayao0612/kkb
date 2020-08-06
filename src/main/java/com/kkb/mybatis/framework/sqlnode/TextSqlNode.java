package com.kkb.mybatis.framework.sqlnode;

import com.kkb.mybatis.framework.config.DynamicContext;
import com.kkb.mybatis.utils.GenericTokenParser;
import com.kkb.mybatis.utils.OgnlUtils;
import com.kkb.mybatis.utils.SimpleTypeRegistry;
import com.kkb.mybatis.utils.TokenHandler;

import java.util.Map;

/**
 * 封装了带有${}的 SQL文本
 */
public class TextSqlNode implements SqlNode{
    private String sqlText;

    public TextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        // 处理${}
        GenericTokenParser tokenParser = new GenericTokenParser("${","}",new BindingTokenHandler(context));
        String sql = tokenParser.parse(sqlText);
        context.appendSql(sql);
    }

    public boolean isDynamic() {
        return sqlText.indexOf("${") > -1;
    }

    /**
     * 处理${}中的内容
     */
    class BindingTokenHandler implements TokenHandler{

        // 为了获取入参对象
        private DynamicContext context;

        public BindingTokenHandler(DynamicContext context) {
            this.context = context;
        }

        /**
         *
         * @param content 这就是${}中的参数名称
         * @return
         */
        @Override
        public String handleToken(String content) {
            Object parameter = context.getBindings().get("_parameter");
            if (SimpleTypeRegistry.isSimpleType(parameter.getClass())){
                return parameter.toString();
            }
            // 使用ONGL表达式获取值
            Object value = OgnlUtils.getValue(content, parameter);
            return value == null ? "": value.toString();
        }
    }
}
