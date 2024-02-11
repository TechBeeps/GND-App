package quran.gnd.com;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class RecyclerAdapterPosts extends RecyclerView.Adapter<RecyclerAdapterPosts.ViewHolder> {
    private Context context;
    private ArrayList<PostClass> dualist;
    public RecyclerAdapterPosts(Context mContext, ArrayList<PostClass> dualist){
        this.context = mContext;
        this.dualist = dualist;
    }
    @NonNull
    @Override
    public RecyclerAdapterPosts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post,parent,false);
        return new RecyclerAdapterPosts.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(dualist.get(position).getName());
        holder.dated.setText(dualist.get(position).getDated());

        if(dualist.get(position).getDetails().length()>=100){
            holder.details.setText(dualist.get(position).getDetails().substring(0,100)+"...");
        }else{
            holder.details.setText(dualist.get(position).getDetails()+"...");
        }
        String image = dualist.get(position).getImage();
        Glide.with(context)
                .load(image)
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),PostDetails.class);
                intent.putExtra("title",dualist.get(position).getName());
                intent.putExtra("dated",dualist.get(position).getDated());
                intent.putExtra("details",dualist.get(position).getDetails());
                intent.putExtra("image",dualist.get(position).getImage());
                view.getContext().startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return dualist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,dated,details;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameFld);
            dated = itemView.findViewById(R.id.dateFld);
            details = itemView.findViewById(R.id.sourceFld);
            img = itemView.findViewById(R.id.ImageFld);
        }
    }
}
