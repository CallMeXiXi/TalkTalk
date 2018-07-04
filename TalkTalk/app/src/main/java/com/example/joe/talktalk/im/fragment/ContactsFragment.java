package com.example.joe.talktalk.im.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joe.talktalk.R;
import com.example.joe.talktalk.base.BaseFragment;
import com.example.joe.talktalk.im.adapter.ContactsAdapter;
import com.example.joe.talktalk.model.ContactsModel;
import com.example.joe.talktalk.ui.sidebar.CharacterParser;
import com.example.joe.talktalk.ui.sidebar.PinyinComparator;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/7/2 0002.
 */

public class ContactsFragment extends BaseFragment {

    //控件
    private RecyclerView rvContacts;
    private ContactsAdapter mAdapter;
    private List<ContactsModel> lists;

    /**
     * 将文字转换成拼音类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音排序ListView数据源
     */
    private PinyinComparator pinyinComparator;
    //上下文
    private Context context;

    private static ContactsFragment instance;

    public static ContactsFragment getInstance() {
        if (instance == null) {
            synchronized (ContactsFragment.class) {
                if (instance == null) {
                    instance = new ContactsFragment();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_contacts_layout, container, false);
        init(view);
        return view;
    }

    @Override
    public void initView(View view) {
        rvContacts = $(view, R.id.rv_contacts);
        //添加Android自带的分割线
        rvContacts.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rvContacts.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();

        lists = new ArrayList<>();
        lists = integrationData(getResources().getStringArray(R.array.name_lists));

        Collections.sort(lists, pinyinComparator);
        mAdapter = new ContactsAdapter(context, lists);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void doClick(int viewId) {

    }

    /**
     * 将联系人中文转换成拼音
     *
     * @param data
     * @return
     */
    private List<ContactsModel> integrationData(String[] data) {
        List<ContactsModel> list = new ArrayList<>();
        for (String str : data) {
            ContactsModel model = new ContactsModel();
            model.setName(str);
            //转换成拼音
            String selling = characterParser.getSelling(str);
            String letter = selling.substring(0, 1).toUpperCase();

            if (letter.matches("[A-Z]")) {
                model.setSortLetters(letter);
            } else if (letter.equals("↑")) {
                model.setSortLetters("↑");
            } else {
                model.setSortLetters("#");
            }
            list.add(model);
        }
        return list;
    }
}
