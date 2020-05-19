package com.lins.mylibrary.dimens.generator;


import com.lins.mylibrary.dimens.constants.DimenTypes;
import com.lins.mylibrary.dimens.utils.MakeUtils;

public class DimenGenerator {

    /**
     * 设计稿尺寸(根据自己设计师的设计稿的宽度填入)
     */
    private static final int DESIGN_WIDTH = 750;

    /**
     * 设计稿高度  没用到
     */
    private static final int DESIGN_HEIGHT = 1920;

    public static void main(String[] args) {
        //在.idea->gradle.xml的<GradleProjectSettings>下一行添加<option name="delegatedBuild" value="false" />
        DimenTypes[] values = DimenTypes.values();
        for (DimenTypes value : values) {
            MakeUtils.makeAll(DESIGN_WIDTH, value, "/androidui/" + DESIGN_WIDTH + "/adapter");
        }

    }

}
