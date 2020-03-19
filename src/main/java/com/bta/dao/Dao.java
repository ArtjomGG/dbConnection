package com.bta.dao;

import com.bta.domain.Book;
import sun.applet.resources.MsgAppletViewer_es;

import java.util.List;

public interface Dao <E> {
    E findById (Long id);
    Integer update (E bookUpd);
    Integer delete (Long id);
    Integer save (E bookSave);
    List<E> findAll ();




}
