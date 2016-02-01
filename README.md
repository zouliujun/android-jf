#jf-android
android library项目，集成volley，eventbus，logback, butterknife，gson，recyclerview，appcompat等库，包含常用UI基类和一些常用工具。

项目结构如下：
- **volley辅助类 cn\change365\framework\network**

- **常用UI基类 cn\change365\framework\ui\base**
```
BaseActivity    //activity基类
BaseFragment    //fragment基类，支持自动刷新
BaseInnerTabFragment    //内存嵌套tab基类
BaseListAdapter    //列表Adapter基类
BaseListFragment    //列表基类，与BaseListAdapter搭配使用
BaseService    //service基类，支持自动刷新
BaseViewPagerAdapter    //滑动页面Adapter基类
BaseViewPagerFragment    //滑动页面基类，与BaseViewPagerAdapter搭配使用
```

- **辅助UI cn\change365\framework\ui** 
```
BadgeView    //提示数字或圆点
EmptyLayout    //内容为空时的提示UI
MyLabelText    //标签+文本
MyRadioButton    //改进RadioButton，支持为其设置隐藏值
MyRadioGroup    //改进RadioGroup，支持根据隐藏值查询，与MyRadioButton搭配使用
SettingMenu    //包含图标、文本、提示符的设置项
SimpleBackActivity    //弹出activity的简单实现，里面只包含一个fragment
SplitLine    //分隔线，中间可包含文字
TimePickerDialogPreference    //时间选择器
WebViewFragment    //简单的webview fragment
```

- **常用工具 cn\change365\framework\utils** 
```
AesUtil    //aes加解密
Base64Util    //base64加解密
CompressUtil    //文件压缩
DateUtil    //日期处理工具
DeviceUtil    //获取设备信息，如版本、屏幕分辨率、imei等
GsonUtil    //gson简单封装
MD5Util    //MD5工具
NotificationUtil    //系统通知相关
NumberUtil    //数字处理
SharedPreferencesUtil    //SharedPreferences辅助
StringUtils    //字符串处理
UIHelper    //UI辅助，如等待窗口、消息窗口等
```