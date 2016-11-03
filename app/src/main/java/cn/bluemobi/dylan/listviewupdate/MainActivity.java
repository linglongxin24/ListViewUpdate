package cn.bluemobi.dylan.listviewupdate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import cn.bluemobi.dylan.listviewupdate.adapter.CommonAdapter;
import cn.bluemobi.dylan.listviewupdate.adapter.CommonViewHolder;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> datas;
    private CommonAdapter commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateOneTest();
    }

    /**
     * 普通适配器的方法
     */
    private void myAdapterTest() {
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview);
        List<String> datas = new ArrayList<>();
        datas.add("普通适配器测试1");
        datas.add("普通适配器测试2");
        datas.add("普通适配器测试3");
        datas.add("普通适配器测试4");
        listView.setAdapter(new MyAdapter(this, datas));
    }

    /**
     * 万能适配器的方法
     */
    private void commonAdapterTest() {
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            datas.add("万能适配器测试" + i);
        }
        listView.setAdapter(new CommonAdapter<String>(this, datas, R.layout.item) {

            @Override
            protected void convertView(View item, String s) {
                TextView textView = CommonViewHolder.get(item, R.id.textView);
                textView.setText(s);
            }
        });
    }

    /**
     * 一般的更新界面
     */
    private void updateTest() {
        final List<String> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add("万能适配器测试" + i);
        }
        final CommonAdapter commonAdapter = new CommonAdapter<String>(this, datas, R.layout.item) {

            @Override
            protected void convertView(View item, String s) {
                TextView textView = CommonViewHolder.get(item, R.id.textView);
                textView.setText(s);
            }
        };
        listView.setAdapter(commonAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datas.set(position, "update 万能适配器测试" + position);
                commonAdapter.notifyDataSetChanged();

            }
        });
    }

    /**
     * 只是局部更新某个界面
     */
    private void updateOneTest() {
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("万能适配器测试" + i);
        }
        commonAdapter = new CommonAdapter<String>(this, datas, R.layout.item) {

            @Override
            protected void convertView(View item, String s) {
                TextView textView = CommonViewHolder.get(item, R.id.textView);
                textView.setText(s);
            }
        };
        listView.setAdapter(commonAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                datas.set(position, "update 万能适配器测试" + position);
                updateSingle(position);
//                updateItem(position);
//                commonAdapter.updateItem(listView, position);

            }
        });
    }

    /**
     * 第一种方法 更新对应view的内容
     *
     * @param position 要更新的位置
     */
    private void updateSingle(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(datas.get(position));
        }
    }

    /**
     * 第二种方法 调用一次getView()方法；Google推荐的做法
     *
     * @param position 要更新的位置
     */
    private void updateItem(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            commonAdapter.getView(position, view, listView);
        }

    }
}
