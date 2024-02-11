package quran.gnd.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterKalma extends RecyclerView.Adapter<RecyclerAdapterKalma.ViewHolder> {
    private Context context;
    private ArrayList<KalmaCLass> dualist;
    public RecyclerAdapterKalma(Context mContext, ArrayList<KalmaCLass> dualist){
        this.context = mContext;
        this.dualist = dualist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kalma_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(position+1)+". "+dualist.get(position).getName());
        holder.kalma.setText(dualist.get(position).getKalma());
        holder.meaning.setText(dualist.get(position).getMeaning());
    }
    @Override
    public int getItemCount() {
        return dualist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,kalma,meaning;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameFld);
            kalma = itemView.findViewById(R.id.kalmaFld);
            meaning = itemView.findViewById(R.id.meaningFld);
        }
    }
}
