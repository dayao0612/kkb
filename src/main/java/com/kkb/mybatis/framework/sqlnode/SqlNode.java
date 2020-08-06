package com.kkb.mybatis.framework.sqlnode;

import com.kkb.mybatis.framework.config.DynamicContext;

public interface SqlNode {
    /**
     *
     * @param context
     */
    void apply(DynamicContext context);
}
