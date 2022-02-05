package com.example.ice_attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class CheckableSpinnerAdapter<T>extends BaseAdapter {


    public static class SpinnerItem<T>{
        private String txt;
        public T item;

        public SpinnerItem(T t , String s){


            item = t;
            txt = s;
        }

    }

    public static class SpinnerCode<T>{
        private String code;
        public T codeT;

        public SpinnerCode(T t, String s){
            code = s;
            codeT = t;

        }

    }

    private Context context;
    private Set<T> selected_items;
    private Set<T> selected_course;
    private List<SpinnerItem<T>> all_items;
    private List<SpinnerCode<T>> course_code;
    private String headerText;

    public CheckableSpinnerAdapter(Context context,String headerText,
                                   List<SpinnerItem<T>> all_items,List<SpinnerCode<T>> course_code,
                                   Set<T> selected_items,Set<T> selected_course){


        this.context = context;
        this.headerText = headerText;
        this.all_items = all_items;
        this.course_code=course_code;
        this.selected_items = selected_items;
        this.selected_course=selected_course;
    }


    @Override
    public int getCount() {
        return all_items.size()+1;
    }

    @Override
    public Object getItem(int i) {

        if(i<1){
            return null;
        }

        else {
            return all_items.get(i-1);

        }

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if(view == null){


            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.spinner_item,viewGroup,false);

            holder = new ViewHolder();
            holder.linearLayout = view.findViewById(R.id.LiId);
            holder.mTextView = view.findViewById(R.id.text);
            holder.mCheckBox = view.findViewById(R.id.Check);
            holder.mCode = view.findViewById(R.id.code);

            view.setTag(holder);



        }

        else{

            holder = (ViewHolder) view.getTag();
        }


        if( i < 1 ) {
            holder.mCheckBox.setVisibility(View.INVISIBLE);
            holder.mCode.setVisibility(View.INVISIBLE);
            holder.mTextView.setText(headerText);
        }


        else{

            final int listPos = i - 1;
            holder.mCheckBox.setVisibility(View.VISIBLE);
            holder.mCode.setVisibility(View.VISIBLE);
            holder.mTextView.setText(all_items.get(listPos).txt);
            holder.mCode.setText(course_code.get(listPos).code);

            final T item = all_items.get(listPos).item;
            final T codeT=course_code.get(listPos).codeT;
            boolean isSel = selected_items.contains(item);

            holder.mCheckBox.setOnCheckedChangeListener(null);
            holder.mCheckBox.setChecked(isSel);

            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        selected_items.add(item);
                       selected_course.add(codeT);

                    }

                    else{

                        selected_items.remove(item);
                        selected_course.remove(codeT);
                    }
                }
            });

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.mCheckBox.toggle();
                }
            });



        }

        return view;

    }


    private class ViewHolder{

        private TextView mTextView;
        private CheckBox mCheckBox;
        private TextView mCode;
        private LinearLayout linearLayout;
    }
}
