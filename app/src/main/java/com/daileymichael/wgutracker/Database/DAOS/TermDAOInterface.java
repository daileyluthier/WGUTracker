package com.daileymichael.wgutracker.Database.DAOS;


import com.daileymichael.wgutracker.Models.Term;

import java.util.List;

public interface TermDAOInterface {

    boolean addTerm(Term term);

    Term getTermById(int termId);

    int getTermCount();

    List<Term> getTerms();

    boolean removeTerm(Term term);
    boolean updateTerm(Term term);
}
