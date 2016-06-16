package com.gdn.x.beirut.services;

import java.util.List;

import com.gdn.x.beirut.entities.Position;

public interface PositionService {

  List<Position> getAllPosition();

  Position getPosition(String positionId) throws Exception;

  List<Position> getPositionByTitle(String title, String storeId);

  Position getPositionDetailByIdAndStoreId(String id, String storeId) throws Exception;

  boolean insertNewPosition(Position position);

  void markForDeletePosition(List<String> id);

  boolean updatePositionTitle(String id, String title);
}
