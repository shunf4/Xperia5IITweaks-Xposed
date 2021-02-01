## Xperia 5 II Tweaks  5兔微调

Various tweaks for Xperia 5 II as an Xposed module

此 Xposed 模块可以对 Xperia 5 II 进行界面和功能的微调

#### Features 功能

- Adjust originally oversized VoLTE icon 调整原来过大的 VoLTE 图标
- Tweak Nav Bar button layout 调整导航栏按键布局  
    You need to understand layout string like this one: 你需要会配置类似这样的配置字符串：  
    `left[.5W],back[1WC];home;recent[1WC],right[.5W]`  
    See also [NavigationBarInflaterView.java](https://github.com/aosp-mirror/platform_frameworks_base/blob/c5d02da0f6553a00da6b0d833b67d3bbe87341e0/packages/SystemUI/src/com/android/systemui/statusbar/phone/NavigationBarInflaterView.java).
- Disable "tap twice to wake" on Always On Display (Ambient Display) 在锁屏始终显示（环境显示）界面上禁止双击唤醒

#### Before Use 使用前须知

- Disable App List mode in EdXposed Manager to use this module. 请先在 EdXposed Manager 中禁用“应用名单”模式。
- Modified setting values take effect after a reboot. 修改设置值后重启生效。

#### License 授权协议

Copyright 2020 shunf4.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
