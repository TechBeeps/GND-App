package quran.gnd.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DateTimes> dateList;
    public RecyclerAdapter(Context mContext, ArrayList<DateTimes> dateList){
        this.context = mContext;
        this.dateList = dateList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ramadan_list,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dateTv.setText(dateList.get(position).getDate());
        holder.startTv.setText(dateList.get(position).getStart());
        holder.endTv.setText(dateList.get(position).getEnd());
        holder.textView.setText(String.valueOf(position+1));
    }
    @Override
    public int getItemCount() {
        return dateList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateTv,startTv,endTv,textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.dateFld);
            startTv = itemView.findViewById(R.id.startFld);
            endTv = itemView.findViewById(R.id.endFld);
            textView = itemView.findViewById(R.id.counter);
        }
    }
}
