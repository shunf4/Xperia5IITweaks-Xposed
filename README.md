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

- EdXposed 0.5.0+ no longer supports this module. EdXposed 0.5.0+ 不再支持此模块。  
  I do not plan to fix this by myself. Though, adding compatibility should be very easy.
  If you want to help, please refer to
  https://github.com/GravityBox/GravityBox/issues/2089 ,
  https://github.com/GravityBox/GravityBox/commit/c274d49f9453e9aef3b7b4871227d4ef98683eee ,
  https://github.com/ElderDrivers/EdXposed/wiki/New-XSharedPreferences ,
   and
  https://github.com/ElderDrivers/EdXposed/commit/84822379184bfdcd830fff997265ac410e200e66#commitcomment-44827982 .

  These links include how to use the new XSharedPreferences and how GravityBox's author did in
  GravityBox to add compatibility. Contribution welcome, thanks.

  我暂时不考虑自己添加对新版 EdXposed 的兼容，如果您有时间，可以考虑帮忙，应该很轻松就能做到。  
  上面的几个链接包括了 EdXposed 如何改动了 XSharedPreferences，以及在新版本中如何使用，以及 GravityBox 的作者  
  是怎么添加兼容性的（修改 Xposed 的 target 版本，再分别根据新版本和老版本做不同的操作获取配置值即可）。  
  欢迎您的贡献，谢谢。

- ~Disable App List mode in EdXposed Manager to use this module. 请先在 EdXposed Manager 中禁用“应用名单”模式。~ Now this module works whether you turn App List mode on. 现在无论是否打开“应用名单”模式，模块都可以用了。
- Modified setting values take effect after a reboot. 修改设置值后重启生效。

#### License 授权协议

Copyright 2020 shunf4.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
