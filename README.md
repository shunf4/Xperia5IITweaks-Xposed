## Xperia 5 II Tweaks  5兔微调

Various tweaks for Xperia 5 II as an Xposed module

此 Xposed 模块可以对 Xperia 5 II 进行界面和功能的微调

**Note: Only Xperia 5 II stock Android 11 is supported. Runs only on latest LSPosed.**

**本应用只支持 Android 11 版本的 Xperia 5 II 官方系统，且只支持 LSPosed 的最新版本。**

#### Features 功能

- Adjust originally oversized VoLTE icon 调整原来过大的 VoLTE 图标
- Tweak Nav Bar button layout 调整导航栏按键布局  
    You need to understand layout string like this one: 你需要会配置类似这样的配置字符串：  
    `left[.5W],back[1WC];home;recent[1WC],right[.5W]`  
    See also [NavigationBarInflaterView.java](https://github.com/aosp-mirror/platform_frameworks_base/blob/c5d02da0f6553a00da6b0d833b67d3bbe87341e0/packages/SystemUI/src/com/android/systemui/statusbar/phone/NavigationBarInflaterView.java).
- Disable "tap twice to wake" on Always On Display (Ambient Display) 在锁屏始终显示（环境显示）界面上禁止双击唤醒
- Reverse Own Order of "All Apps" in Launcher 让启动器所有应用列表中“初始顺序”排列逆序

#### Before Use 使用前须知

- **Backup up settings and switch to LSPosed from EdXposed first before upgrading from 1.3 to 1.4 or above. 从 1.3 升级到 1.4 之前，请先备份本应用的设置，并从 EdXposed 切换到 LSPosed。**
  Because this module has been updated to adapt to latest LSPosed only, and how EdXposed/LSPosed 93+ handles app preferences was updated. 因为本应用更新后只适配最新的 LSPosed，并且 Xposed API 93+ 后，处理应用偏好设置的机制被更新了。
- You must enable "Resource hooks" in LSPosed to make UI-related tweaks work. 要使与 UI 有关的微调生效，你必须在 LSPosed 中启用**资源钩子**。
- Modified setting values take effect after a reboot. 修改设置值后重启生效。


#### License 授权协议

Copyright 2020-2021 shunf4.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
