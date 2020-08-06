package com.kkb.mybatis.framework.sqlsource;

import com.kkb.mybatis.framework.config.BoundSql;
import com.kkb.mybatis.framework.config.DynamicContext;
import com.kkb.mybatis.framework.sqlnode.SqlNode;
import com.kkb.mybatis.framework.sqlnode.TextSqlNode;
import com.kkb.mybatis.utils.GenericTokenParser;
import com.kkb.mybatis.utils.ParameterMappingTokenHandler;

/**
 * 封装了不带有${}和动态标签的SQL信息，并提供对他们的处理接口
 * 注意事项：
 * 当处理#{}时，只需要处理一次就可以获取对应的SQL语句了。
 * select * from user where id = #{}
 * select * from user where id = ?
 */
public class RawSqlSource implements SqlSource{
    // 一个静态的SqlSource（StaticSqlSource）
    private SqlSource sqlSource ;

    public RawSqlSource(SqlNode mixedSqlNode) {
        // 真正处理#{}的逻辑要放在该构造方法中
        // 把处理之后的结果，封装成一个静态的SqlSource（StaticSqlSource）

        // 1.处理所有的SQL节点，获取合并之后的完整的SQL语句（可能还带有#{}）
        DynamicContext context = new DynamicContext(null);
        mixedSqlNode.apply(context);
        String sqlText = context.getSql();
        // 2.调用SqlSource的处理逻辑，对于#{}进行处理
        // 处理#{}
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{","}",tokenHandler);
        String sql = tokenParser.parse(sqlText);

        sqlSource = new StaticSqlSource(sql,tokenHandler.getParameterMappings());
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return sqlSource.getBoundSql(param);
    }
}
