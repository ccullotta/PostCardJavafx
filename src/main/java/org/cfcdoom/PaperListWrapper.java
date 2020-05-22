package org.cfcdoom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaperListWrapper  implements Serializable {
    private List<org.cfcdoom.Paper> papers;

    public PaperListWrapper(){

    }
    public PaperListWrapper(List<org.cfcdoom.Paper> p ){
        this.papers = new ArrayList<>();
        papers.addAll(p);
    }
    public List<org.cfcdoom.Paper> getPapers() {
        return papers;
    }
}
