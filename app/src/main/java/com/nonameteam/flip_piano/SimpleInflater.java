package com.nonameteam.flip_piano;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Simple wrapper class for {@link LayoutInflater}.
 * Useful for multiple inflations from same parent.
 * For single inflation use static implementations.
 */
public class SimpleInflater {
    private Context context;
    private ViewGroup parent;

    public SimpleInflater(Context context, ViewGroup parent) {
        this.context = context;
        this.parent = parent;
    }

    public SimpleInflater(Activity activity, View parent) {
        this(activity.getApplicationContext(), (ViewGroup) parent);
    }

    public SimpleInflater(ViewGroup parent) {
        this(parent.getContext(), parent);
    }

    @SuppressLint("ResourceType")
    public View inflate(@LayoutRes int id, boolean attachToRoot) {
        View view = LayoutInflater.from(context)
                .inflate(id, parent, attachToRoot);

        if (attachToRoot) return getLastChild(parent);
        else return view;
    }

    @SuppressLint("ResourceType")
    public View inflate(@LayoutRes int id) {
        return this.inflate(id, true);
    }

    public static View inflate(@NonNull ViewGroup parent, @LayoutRes int id, boolean attachToRoot) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(id, parent, attachToRoot);

        if (attachToRoot) return getLastChild(parent);
        else return view;
    }

    public static View inflate(@NonNull ViewGroup parent, @LayoutRes int id) {
        return SimpleInflater.inflate(parent, id, true);
    }

    @Nullable
    public static View getLastChild(@NonNull ViewGroup parent) {
        for (int i = parent.getChildCount() - 1; i >= 0; i--) {
            View child = parent.getChildAt(i);

            if (child != null) return child;
        }
        return null;
    }
}
