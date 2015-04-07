package com.manishk.webcrawler.revisit;

import com.manishk.webcrawler.persistence.LastVisitedDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by manish on 07/04/15.
 */
@Service
public class RevisitUrlServiceImpl implements RevisitUrlService {
    private static  final Logger log = LoggerFactory.getLogger(RevisitUrlService.class);

    @Autowired
    private LastVisitedDao lastVisitedDao;

    @Override
    public List<String> getLinkToRevisit(Date date)
    {
        List<String> links =lastVisitedDao.select(date);
        return links;
    }
}
