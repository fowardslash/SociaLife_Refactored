package com.example.projectmain.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.PerformanceHintManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.projectmain.Adapter.PostAdapter;
import com.example.projectmain.Adapter.ViewPagerAdapter;
import com.example.projectmain.Database.DB;
import com.example.projectmain.Global.OnColorChangeListener;
import com.example.projectmain.MainActivity;
import com.example.projectmain.Model.Post;
import com.example.projectmain.Model.User;
import com.example.projectmain.R;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.timepicker.TimeFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment  implements OnColorChangeListener {

    public HomeFragment() {
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public static RecyclerView recyclerView;
    PostAdapter adapter;

    DB db;

    User user;

    List<Post> posts = null;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";

    private static final String KEY_EMAIL = "email";

    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE_LINK = "linkImage";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    List<String> listName;
    ImageButton btnHeart;
    ImageButton btnMenu;


    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    ViewPager2 viewPager;
    TabLayout tlPostType;
    private int[] tabTittle = new int[]{R.string.tab1, R.string.tab2, R.string.tab3};
    private int[] tabIcons = new int[]{R.drawable.compass_solid, R.drawable.star_solid, R.drawable.share_solid};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }


    @SuppressLint({"SuspiciousIndentation", "NotifyDataSetChanged"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View viewPost = getLayoutInflater().inflate(R.layout.post, null);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext().getApplicationContext());

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String linkImage = sharedPreferences.getString(KEY_IMAGE_LINK, null);

        db = new DB(getContext().getApplicationContext());

        user = db.getUser(email);
        listName = db.getListNameID();

        SQLiteDatabase database = db.getReadableDatabase();

        Cursor cursorGetUser = database.rawQuery("SELECT u.* FROM user u JOIN post p on u.id = p.iduser", null);
        List<Integer> list = new ArrayList<Integer>();

        Post p = new Post();
        while (cursorGetUser.moveToNext()) {
            int idfit = cursorGetUser.getInt(0);

            list.add(idfit);
            int idUser = cursorGetUser.getInt(0);

            String userName = cursorGetUser.getString(1);


        }


        //   Log.d("LinkImage: ", db.getImgAvata(3));


        //   Log.d("ID: ",  db.getName(list.get(i)));

        posts = getPostVip(user.getId());

        //  posts = getPostMerge(user.getId());
        //  posts = getSharePost();
        //List<Post> postNormal = getPostVip(user.getId());

//        adapter = new PostAdapter(getActivity(), posts);
//        adapter.notifyDataSetChanged();
//
//
//        btnMenu = viewPost.findViewById(R.id.btnOptions);
//        recyclerView = view.findViewById(R.id.render);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        tabLayout = view.findViewById(R.id.tablayout);
        viewPager2 = view.findViewById(R.id.viewPager2);
        viewPagerAdapter = new ViewPagerAdapter((FragmentActivity) getContext());
        viewPager2.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon((tabIcons[position]));
                //setupTabIcons(position);

            }
        }).attach();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
//        if (posts != null)
//            posts.clear();
//        posts.addAll(getPostVip(user.getId()));
//        //posts.addAll(getPostMerge(user.getId()));
//        //posts.addAll(getSharePost());
//        adapter = new PostAdapter(getContext().getApplicationContext(), posts);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    public List<Post> getPost() {
        String[] column = {"content", "image", "comment_count", "datetime"};
        List<Post> posts = new ArrayList<Post>();
        SQLiteDatabase myDB = db.getWritableDatabase();

        Cursor cursor = myDB.query("post", null, null, null, "id", null, "id desc");

        //Cursor cursorGetUser = myDB.rawQuery("SELECT u.* FROM user u JOIN post p on u.id = p.iduser", null);

        while (cursor.moveToNext()) {
            int idPost = cursor.getInt(0);
            int iduser = cursor.getInt(1);
            String content = cursor.getString(2);
            String img = cursor.getString(3);
            int count_like = cursor.getInt(4);
            int count_comment = cursor.getInt(5);
            int count_share = cursor.getInt(6);
            String time = cursor.getString(7);
            Calendar c = Calendar.getInstance();
            if (time == null) {
                time = "0";
            }
            long t = c.getTimeInMillis() - Long.parseLong(time);
            long hr = (t / (1000 * 60 * 60));
            long min = (t / (1000 * 60));

            String timedifference;

            if (hr >= 24 * 365) {
                timedifference = String.valueOf(hr / 24 / 365) + " Năm trước";
            } else if (hr >= 24 * 30) {
                timedifference = String.valueOf(hr / 24 / 30) + " Tháng trước";
            } else if (hr >= 24 * 7) {
                timedifference = String.valueOf(hr / 24 / 7) + " Tuần trước";
            } else if (hr >= 24) {
                timedifference = String.valueOf(hr / 24) + " Ngày trước";
            } else {
                timedifference = String.valueOf(hr) + " Giờ trước";
            }
            if (hr <= 1) {
                timedifference = String.valueOf(min) + " Phút trước";
            } else if (min <= 1) {
                timedifference = String.valueOf(t / (1000) + " Giây trước");
            }
            DateFormat d = SimpleDateFormat.getDateInstance();
            d.setCalendar(c);
            String test = d.format(new Date(Long.parseLong(time)));
            timedifference += " (Đăng ngày " + test + ")";
            posts.add(new Post(idPost, iduser, db.getImgAvata(iduser), img, db.getName(iduser), db.getName(iduser), String.valueOf(count_like), content, timedifference));

        }
        return posts;
    }

//    public ArrayList<Post> getSharePost() {
//
//        ArrayList<Post> sharePosts = new ArrayList<>();
//        SQLiteDatabase database = db.getWritableDatabase();
//        Cursor cursor = database.query("share", null, null, null, null, null, null, null);
//        while (cursor.moveToNext()) {
//
//            Post post = db.getPost(cursor.getInt(2));
//            User u = db.getUser(cursor.getInt(1));
//            Post shareP = new Post(post.getIduser(), db.getImgAvata(post.getIduser()), post.getImgPost(), db.getName(post.getIduser()), db.getName(post.getIduser()), post.getNumber_like(), post.getPostContent(), post.getTime());
//
//            sharePosts.add(new Post(db.getImgAvata(user.getId()), shareP, u.getName(), u.getName(), post.getNumber_like(), post.getPostContent(), post.getTime()));
//        }
//
//        return sharePosts;
//    }

    public List<Post> getPostVip(int myID) {
        String[] column = {"content", "image", "comment_count", "datetime"};
        List<Post> posts = new ArrayList<Post>();
        SQLiteDatabase myDB = db.getWritableDatabase();

        //Cursor cursor = myDB.query("post", null, null, null, "id", null, "id desc");

        Cursor cursor = myDB.rawQuery("SELECT * FROM post p join follower f WHERE  (p.iduser = f.idfollowing or p.iduser = ?) and f.iduser = ? GROUP BY p.id ORDER BY p.id DESC ", new String[]{String.valueOf(myID), String.valueOf(myID)});


        //Cursor cursorGetUser = myDB.rawQuery("SELECT u.* FROM user u JOIN post p on u.id = p.iduser", null);

        while (cursor.moveToNext()) {
            int idPost = cursor.getInt(0);
            int iduser = cursor.getInt(1);
            String content = cursor.getString(2);
            String img = cursor.getString(3);
            int count_like = cursor.getInt(4);
            int count_comment = cursor.getInt(5);
            int count_share = cursor.getInt(6);
            String time = cursor.getString(7);
            Calendar c = Calendar.getInstance();
            if (time == null) {
                time = "0";
            }
            long t = c.getTimeInMillis() - Long.parseLong(time);
            long hr = (t / (1000 * 60 * 60));
            long min = (t / (1000 * 60));

            String timedifference;

            if (hr >= 24 * 365) {
                timedifference = String.valueOf(hr / 24 / 365) + " Năm trước";
            } else if (hr >= 24 * 30) {
                timedifference = String.valueOf(hr / 24 / 30) + " Tháng trước";
            } else if (hr >= 24 * 7) {
                timedifference = String.valueOf(hr / 24 / 7) + " Tuần trước";
            } else if (hr >= 24) {
                timedifference = String.valueOf(hr / 24) + " Ngày trước";
            } else {
                timedifference = String.valueOf(hr) + " Giờ trước";
            }
            if (hr <= 1) {
                timedifference = String.valueOf(min) + " Phút trước";
            } else if (min <= 1) {
                timedifference = String.valueOf(t / (1000) + " Giây trước");
            }
            DateFormat d = SimpleDateFormat.getDateInstance();
            d.setCalendar(c);
            String test = d.format(new Date(Long.parseLong(time)));
            timedifference += " (Đăng ngày " + test + ")";

            posts.add(new Post(idPost, iduser, db.getImgAvata(iduser), img, db.getName(iduser), db.getName(iduser), String.valueOf(count_like), content, timedifference));

        }
        return posts;
    }


    @Override
    public void onColorChanged(Drawable color) {
        tabLayout.setBackgroundDrawable(color);
    }
}