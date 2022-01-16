package com.dannyweston.mdp_cw3.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.dao.Run;

import java.util.ArrayList;
import java.util.List;

public class RunRecyclerViewAdapter extends RecyclerView.Adapter<RunRecyclerViewAdapter.ViewHolder> {
    private List<Run> _data = new ArrayList<>();
    private final LayoutInflater _inflater;
    private ItemClickListener _clickListener;

    RunRecyclerViewAdapter(Context context) {
        this._inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = _inflater.inflate(R.layout.run_recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = _data.get(position).toString();
        holder._textRun.setText(text);
        holder._textRun.setTag(_data.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return _data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView _textRun;

        ViewHolder(View itemView) {
            super(itemView);
            _textRun = itemView.findViewById(R.id.txt_OverviewRun);
            _textRun.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (_clickListener != null)
                _clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setData(List<Run> runs){
        _data = runs;
        notifyDataSetChanged();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this._clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
