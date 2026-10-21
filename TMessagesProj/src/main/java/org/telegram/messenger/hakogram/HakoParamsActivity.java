package org.telegram.messenger.hakogram;

import android.content.Context;
import android.view.View;

import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.UItem;
import org.telegram.ui.Components.UniversalAdapter;
import org.telegram.ui.Components.UniversalRecyclerView;

/**
 * HAKOGRAM: Параметры — под Докс beta
 * В стандартном стиле Telegram (UniversalRecyclerView, Theme.key_windowBackgroundGray)
 */
public class HakoParamsActivity extends BaseFragment {

    private UniversalRecyclerView listView;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(org.telegram.messenger.R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("Параметры");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) finishFragment();
            }
        });
        listView = new UniversalRecyclerView(this, this::fillItems, this::onClick, null);
        listView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        fragmentView = listView;
        return fragmentView;
    }

    private void fillItems(java.util.ArrayList<UItem> items, UniversalAdapter adapter) {
        items.add(UItem.asHeader("Пересылка"));
        items.add(UItem.asCheck(100, "Пересылка сообщений").setChecked(HakoConfig.isForwardEnabled()));
        if (HakoConfig.isForwardEnabled()) {
            items.add(UItem.asCheck(101, "Пересылка с каналов").setChecked(HakoConfig.isForwardFromChannels()).setPad(1));
            items.add(UItem.asCheck(102, "Пересылка с групп/супер").setChecked(HakoConfig.isForwardFromGroups()).setPad(1));
            items.add(UItem.asCheck(103, "Пересылка с защищённых чатов").setChecked(HakoConfig.isForwardFromProtected()).setPad(1));
            items.add(UItem.asCheck(104, "включить все и дополнительные").setChecked(HakoConfig.isForwardAll()).setPad(1));
        }
        items.add(UItem.asShadow(null));
        items.add(UItem.asHeader("Папки"));
        items.add(UItem.asCheck(200, "Неогр папки").setChecked(HakoConfig.isUnlimitedFolders()));
        items.add(UItem.asShadow(null));
        items.add(UItem.asHeader("Позволяет создавать папки без ограничения Premium"));
    }

    private void onClick(UItem item, View view, int position, float x, float y) {
        boolean needUpdate = true;
        switch (item.id) {
            case 100:
                HakoConfig.setForwardEnabled(!HakoConfig.isForwardEnabled());
                break;
            case 101:
                HakoConfig.setForwardFromChannels(!HakoConfig.isForwardFromChannels());
                if (!HakoConfig.isForwardFromChannels() && HakoConfig.isForwardAll()) HakoConfig.setForwardAll(false);
                else if (HakoConfig.isForwardFromChannels() && HakoConfig.isForwardFromGroups() && HakoConfig.isForwardFromProtected()) HakoConfig.setForwardAll(true);
                break;
            case 102:
                HakoConfig.setForwardFromGroups(!HakoConfig.isForwardFromGroups());
                if (!HakoConfig.isForwardFromGroups() && HakoConfig.isForwardAll()) HakoConfig.setForwardAll(false);
                else if (HakoConfig.isForwardFromChannels() && HakoConfig.isForwardFromGroups() && HakoConfig.isForwardFromProtected()) HakoConfig.setForwardAll(true);
                break;
            case 103:
                HakoConfig.setForwardFromProtected(!HakoConfig.isForwardFromProtected());
                if (!HakoConfig.isForwardFromProtected() && HakoConfig.isForwardAll()) HakoConfig.setForwardAll(false);
                else if (HakoConfig.isForwardFromChannels() && HakoConfig.isForwardFromGroups() && HakoConfig.isForwardFromProtected()) HakoConfig.setForwardAll(true);
                break;
            case 104:
                boolean newAll = !HakoConfig.isForwardAll();
                HakoConfig.setForwardAll(newAll);
                if (!newAll) {
                    HakoConfig.setForwardFromChannels(false);
                    HakoConfig.setForwardFromGroups(false);
                    HakoConfig.setForwardFromProtected(false);
                }
                break;
            case 200:
                HakoConfig.setUnlimitedFolders(!HakoConfig.isUnlimitedFolders());
                break;
            default:
                needUpdate = false;
        }
        if (needUpdate && fragmentView instanceof UniversalRecyclerView) {
            ((UniversalRecyclerView) fragmentView).adapter.update(true);
        }
    }
}
