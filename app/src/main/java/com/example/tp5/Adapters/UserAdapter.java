package com.example.tp5.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp5.Bean.User;
import com.example.tp5.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;
    private List<User> userList;

    // --- NOUVEAU ---
    // Variable pour stocker notre listener
    private OnUserClickListener listener;

    // --- NOUVELLE INTERFACE ---
    // Définit les actions que l'activité peut écouter
    public interface OnUserClickListener {
        void onUserClick(int position);
        // Vous pouvez ajouter d'autres interactions ici (ex: onUserLongClick)
    }

    // --- CONSTRUCTEUR MIS A JOUR ---
    // Il accepte maintenant le listener en paramètre
    public UserAdapter(Context context, List<User> userList, OnUserClickListener listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener; // On assigne le listener
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        // On passe le listener au constructeur du ViewHolder
        return new UserViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvUserId.setText(String.valueOf(user.getId()));
        holder.tvUserName.setText(user.getFirstName() + " " + user.getLastName());
        holder.tvUserAge.setText(String.valueOf(user.getAge()));
        holder.tvUserMajor.setText(user.getMajor());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    // --- VIEWHOLDER MIS A JOUR ---
    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserId, tvUserName, tvUserAge, tvUserMajor;

        // Le constructeur reçoit le listener
        public UserViewHolder(@NonNull View itemView, OnUserClickListener listener) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserAge = itemView.findViewById(R.id.tvUserAge);
            tvUserMajor = itemView.findViewById(R.id.tvUserMajor);

            // --- ON AJOUTE LE CLICK LISTENER A L'ITEM ENTIER ---
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // On vérifie que le listener n'est pas null
                    // et que la position est valide
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // On appelle la méthode de l'interface
                            listener.onUserClick(position);
                        }
                    }
                }
            });

            // (Optionnel) Exemple pour un clic long :
            /*
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // listener.onUserLongClick(position);
                            return true; // Indique que le clic a été consommé
                        }
                    }
                    return false;
                }
            });
            */
        }
    }
}