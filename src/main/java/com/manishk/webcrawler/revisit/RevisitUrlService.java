package com.manishk.webcrawler.revisit;

import java.util.Date;
import java.util.List;

/**
 * Interface for RevisitURL service
 * Created by manish on 07/04/15.
 */
public interface RevisitUrlService {

    List<String> getLinkToRevisit(Date date);
}
