package cn.change365.framework.event;

import cn.change365.framework.utils.StringUtils;

/**
 * Created by Jack on 2015/10/28.
 */
public class TabChangeEvent {
    public static final int TYPE_SELECT = 0;
    public static final int TYPE_UNSELECT = 1;
    public static final int TYPE_RESELECT = 2;
    public int type;
    public String tabTag;

    public TabChangeEvent(int type, String tabTag) {
        this.type = type;
        this.tabTag = tabTag;
    }

    @Override
    public String toString() {
        return StringUtils.toOneLineEntityString(this);
    }
}
