package pro.offline.offlinedatasync.controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import pro.offline.offlinedatasync.R;
import pro.offline.offlinedatasync.model.UserInfo;


public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder> {
    private Context context;
    private List<UserInfo> userInfoList;

    public UserInfoAdapter(Context context, List<UserInfo> userInfoList) {
        this.context = context;
        this.userInfoList = userInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.user_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            holder.tvName.setText("" + userInfoList.get(position).getName());
            holder.tvPhone.setText("" + userInfoList.get(position).getPhone());
            holder.tvEmail.setText("" + userInfoList.get(position).getEmail());
            if (userInfoList.get(position).getDesignation() != null) {
                holder.tvDesignation.setVisibility(View.VISIBLE);
                holder.tvDesignation.setText("Designation: " + userInfoList.get(position).getDesignation());
            }
            if (userInfoList.get(position).getCompanyName() != null) {
                holder.tvCompanyName.setVisibility(View.VISIBLE);
                holder.tvCompanyName.setText("Company Name: " + userInfoList.get(position).getCompanyName());
            }
        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return userInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvDesignation, tvEmail, tvPhone, tvCompanyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesignation = itemView.findViewById(R.id.tvDesignation);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
        }
    }

}