package com.freshappbooks.notes;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<Note> notes;
    private OnNoteClickListener mOnNoteClickListener;

    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    interface OnNoteClickListener {
        void onNoteClick(int position);
        void onLongNoteClick (int position);
    }


    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        mOnNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        Note note = notes.get(position);
        holder.mTextViewTitle.setText(note.getTitle());
        holder.mTextViewDesc.setText(note.getDescription());
        holder.mTextViewDayOfWeek.setText("День недели: " + note.getDayOfWeek());
        int colorId;
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);
                break;
            case 3:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_dark);
                break;
            case 4:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
            default:
                colorId = holder.itemView.getResources().getColor(android.R.color.black);
        }
        holder.mTextViewTitle.setBackgroundColor(colorId);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder { // Держатель видимых частей

        private TextView mTextViewTitle;
        private TextView mTextViewDesc;
        private TextView mTextViewDayOfWeek;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.textViewTitle);
            mTextViewDesc = itemView.findViewById(R.id.textViewDescription);
            mTextViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnNoteClickListener != null) {
                        mOnNoteClickListener.onNoteClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnNoteClickListener != null) {
                        mOnNoteClickListener.onLongNoteClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }
}
