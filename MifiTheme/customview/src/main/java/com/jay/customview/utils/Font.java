package com.jay.customview.utils;

/**
 * 字体枚举
 */
public enum Font {
    DEFAULT("default", "default"),
    MIUI_BOLD("miui_bold", "fonts/MIUI-Bold.ttf"),
    MIUI_LIGHT("miui_light", "fonts/MIUI-Light.ttf"),
    MIUI_NORMAL("miui_normal", "fonts/MIUI-Normal.ttf"),
    PT_DIN("pt_din", "fonts/pt_din_condensed_cyrillic.ttf"),
    DIN_MED("din_med", "fonts/dincond_medium.otf"),
    WATERMARK("watermark", "fonts/watermark_data_view_bold.otf");

    public String mFontName = null;
    public String mFontPath = null;

    Font(String fontName, String fontPath) {
        this.mFontName = fontName;
        this.mFontPath = fontPath;
    }
}