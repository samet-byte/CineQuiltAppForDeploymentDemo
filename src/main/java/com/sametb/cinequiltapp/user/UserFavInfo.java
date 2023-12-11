package com.sametb.cinequiltapp.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 8.12.2023 4:33 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFavInfo {

    private Integer favMetaId;
    private String title;


//    public static List<UserFavInfo> fromUser(User user) {
//
//        return user.getFavourites().stream().map(fav -> {
//            UserFavInfo userFavInfo = new UserFavInfo();
//            userFavInfo.setFavMetaId(fav.getId());
//            userFavInfo.setTitle(fav.getTitle());
//            return userFavInfo;
//        }).toList();
//
//    }


}
