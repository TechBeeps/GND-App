package quran.gnd.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerAdapterDua extends RecyclerView.Adapter<RecyclerAdapterDua.ViewHolder> {
    private Context context;
    private ArrayList<DuaClass> dualist;
    public RecyclerAdapterDua(Context mContext, ArrayList<DuaClass> dualist){
        this.context = mContext;
        this.dualist = dualist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dua_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(String.valueOf(position+1)+". "+dualist.get(position).getName());
        holder.dua.setText(dualist.get(position).getDua());
        holder.meaning.setText(dualist.get(position).getTranslation());
        holder.source.setText(dualist.get(position).getSource());
    }
    @Override
    public int getItemCount() {
        return dualist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,dua,meaning,source,count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameFld);
            dua = itemView.findViewById(R.id.duaFld);
            meaning = itemView.findViewById(R.id.meaningFld);
            source = itemView.findViewById(R.id.sourceFld);
            //count = itemView.findViewById(R.id.counterFld);
        }
    }
}
