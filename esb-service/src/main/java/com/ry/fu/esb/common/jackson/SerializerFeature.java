package com.ry.fu.esb.common.jackson;

/**
 * @Author: telly
 * @Description: Jackson自定义序列值为null的枚举类型
 * @Date: Create in 14:41 2018/1/30
 */
public enum SerializerFeature {
    WriteNullListAsEmpty,
    WriteNullStringAsEmpty,
    WriteNullNumberAsZero,
    WriteNullBooleanAsFalse;

    public final int mask;

    SerializerFeature() {
        mask = (1 << ordinal());
    }
}
