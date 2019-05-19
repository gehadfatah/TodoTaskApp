package com.gehad.todotask.ui.base;

public interface Presenter<T extends MvpView> {

    void onAttach(T mvpView);

    void onDetach();

    
}
