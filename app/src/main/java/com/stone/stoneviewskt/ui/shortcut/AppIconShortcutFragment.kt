package com.stone.stoneviewskt.ui.shortcut

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.base.BaseActivity
import com.stone.stoneviewskt.common.BaseBindFragment
import com.stone.stoneviewskt.databinding.FragmentAppIconShortcutBinding

/**
 * desc:    api25, 7.1以后，桌面长按图标出现快捷方式;
 *          弹出的快捷菜单项，长按后拖到桌面空白处，会生成 一个该快捷项 在桌面上。
 *          静态的快捷项配置项会先加载；动态的加载，在代码触发后，才添加；只有动态加载的才可以通过代码删除
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/6/8 08:52
 */
class AppIconShortcutFragment: BaseBindFragment<FragmentAppIconShortcutBinding>(R.layout.fragment_app_icon_shortcut) {

    override fun onPreparedView(savedInstanceState: Bundle?) {
        super.onPreparedView(savedInstanceState)

        // 动态代码设置后，可切到桌面 长按图标，查看效果

        mBind.btnAdd.setOnClickListener {
            addMenu()
        }

        mBind.btnRemove.setOnClickListener {
            remove()
        }
    }

    private fun addMenu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutInfoList: MutableList<ShortcutInfoCompat> = ArrayList()
            // 快捷菜单项
            val builder: ShortcutInfoCompat.Builder =
                ShortcutInfoCompat.Builder(requireContext(), "admin999") // id 唯一标识，内容随意
            builder.setShortLabel("st-music") // 桌上快捷单项的 名称
                .setLongLabel("stone-music") // 弹出的快捷菜单项的 名称; 如果太长了，会显示 shortLabel
                .setIcon(IconCompat.createWithResource(requireContext(), R.mipmap.satellite_music))
                .setIntent(
                    Intent(Intent.ACTION_MAIN, null, requireContext(), BaseActivity::class.java)
                        .putExtra(BaseActivity.KEY_FRAGMENT_NAME, SatelliteMusicFragment::class.java.canonicalName)
                    /*
                     如果用 BaseActivity.KEY_FRAGMENT, SatelliteMusicFragment::class.java
                     会报异常：Bad value in PersistableBundle key=KEY_FRAGMENT ...
                     而改成，入参为 string 类型 SatelliteMusicFragment::class.java.canonicalName后，则正常了
                     所以在 BaseActivity 中增加 KEY_FRAGMENT_NAME 相关代码
                     */
                )
            shortcutInfoList.add(builder.build())
            // 内部调用 ShortcutManager#addDynamicShortcuts(shortcut)
            ShortcutManagerCompat.addDynamicShortcuts(requireContext(), shortcutInfoList)
        }
    }

    private fun remove() {
        val list = ShortcutManagerCompat.getDynamicShortcuts(requireContext()).map { it.id }
        // 删除动态添加的 指定的快捷项
        ShortcutManagerCompat.removeDynamicShortcuts(requireContext(), list)
        // 删除所有动态添加的快捷项
        ShortcutManagerCompat.removeAllDynamicShortcuts(requireContext())

    }
}