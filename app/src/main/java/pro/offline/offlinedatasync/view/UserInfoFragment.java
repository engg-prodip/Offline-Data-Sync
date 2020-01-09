package pro.offline.offlinedatasync.view;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pro.offline.offlinedatasync.R;
import pro.offline.offlinedatasync.controler.UserInfoAdapter;
import pro.offline.offlinedatasync.model.UserInfo;
import pro.offline.offlinedatasync.sqlite.DatabaseAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private DatabaseAdapter dbAdapter;
    private List<UserInfo> userInfoList = new ArrayList<>();
    private LinearLayoutManager llm;
    private GridLayoutManager glm;
    private UserInfoAdapter userInfoAdapter;
    private TextView notFound;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        notFound = view.findViewById(R.id.notFound);
        dbAdapter = new DatabaseAdapter(context);
        userInfoList.clear();
        dbAdapter.open();
        userInfoList = dbAdapter.getAllOffLineUser();
        dbAdapter.close();

        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        glm = new GridLayoutManager(context, 1);
        userInfoAdapter = new UserInfoAdapter(context, userInfoList);
        recyclerView.setLayoutManager(glm);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(userInfoAdapter);

        if (userInfoList.size()>0){
            notFound.setVisibility(View.GONE);
        }else {
            notFound.setVisibility(View.VISIBLE);
        }

        return view;
    }

}
