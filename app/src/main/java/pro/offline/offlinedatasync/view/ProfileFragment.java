package pro.offline.offlinedatasync.view;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import pro.offline.offlinedatasync.R;
import pro.offline.offlinedatasync.response.SignUpResponse;
import pro.offline.offlinedatasync.service.RetrofitClient;
import pro.offline.offlinedatasync.sharedpreference.StoreData;
import pro.offline.offlinedatasync.sqlite.DatabaseAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context context;
    private StoreData store;
    private CircleImageView circleImageView;
    private TextView tvName, tvDesignation, tvEmail, tvPhone, tvCompanyName;
    private DatabaseAdapter databaseAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvName.setText("" + store.getUserName());
        if (!store.getDesignation().isEmpty() || store.getDesignation() != null) {
            tvDesignation.setVisibility(View.VISIBLE);
            tvDesignation.setText("" + store.getDesignation());
        }
        if (!store.getCompanyName().isEmpty() || store.getCompanyName() != null) {
            tvCompanyName.setVisibility(View.VISIBLE);
            tvCompanyName.setText("" + store.getCompanyName());
        }

        tvEmail.setText("" + store.getUserEmail());
        tvPhone.setText("" + store.getUserPhone());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        store = StoreData.getInstance(context);
        tvName = view.findViewById(R.id.tvName);
        tvDesignation = view.findViewById(R.id.tvDesignation);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvCompanyName = view.findViewById(R.id.tvCompanyName);
        databaseAdapter = new DatabaseAdapter(context);
        return view;
    }
}
