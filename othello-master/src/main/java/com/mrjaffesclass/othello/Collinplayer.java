/*
 * A player for testing purposes
 * Copyright 2017 Roger Jaffe
 * All rights reserved
 */

package com.mrjaffesclass.othello;

import java.util.ArrayList;

/**
 * Test player
 */
public class Collinplayer extends Player {

  /**
   * Constructor
   * @param name Player's name
   * @param color Player color: one of Constants.BLACK or Constants.WHITE
   */
  public Collinplayer(int color) {  
    super(color);
    
  }

  /**
   *
   * @param board
   * @return The player's next move
   */
  @Override
  public Position getNextMove(Board board) {
    ArrayList<Position> list = this.getLegalMoves(board);
    if (list.size() > 0) {
      // This is the code to get the best move
      return getBestMove(board, list);
    } else {
      return null;
    }
  }
  
  /**
   * Is this a legal move?
   * @param player Player asking
   * @param positionToCheck Position of the move being checked
   * @return True if this space is a legal move
   */
  private boolean isLegalMove(Board board, Position positionToCheck) {
    for (String direction : Directions.getDirections()) {
      Position directionVector = Directions.getVector(direction);
      if (step(board, positionToCheck, directionVector, 0)) {
        return true;
      }
    }
    return false;
  }
  
  /**
   * Traverses the board in the provided direction. Checks the status of
   * each space:   
   * a. If it's the opposing player then we'll move to the next
   *    space to see if there's a blank space
   * b. If it's the same player then this direction doesn't represent
   *    a legal move
   * c. If it's a blank AND if it's not the adjacent square then this
   *    direction is a legal move. Otherwise, it's not.
   * 
   * @param player  Player making the request
   * @param position Position being checked
   * @param direction Direction to move
   * @param count Number of steps we've made so far
   * @return True if we find a legal move
   */
  private boolean step(Board board, Position position, Position direction, int count) {
    Position newPosition = position.translate(direction);
    int color = this.getColor();
    if (newPosition.isOffBoard()) {
      return false;
    } else if (board.getSquare(newPosition).getStatus() == -color) {
      return this.step(board, newPosition, direction, count+1);
    } else if (board.getSquare(newPosition).getStatus() == color) {
      return count > 0;
    } else {
      return false;
    }
  }
  
  /**
   * Get the legal moves for this player on the board
   * @param board
   * @return True if this is a legal move for the player
   */
  public ArrayList<Position> getLegalMoves(Board board) {
    int color = this.getColor();
    ArrayList list = new ArrayList<>();
    for (int row = 0; row < Constants.SIZE; row++) {
      for (int col = 0; col < Constants.SIZE; col++) {
        if (board.getSquare(this, row, col).getStatus() == Constants.EMPTY) {
          Position testPosition = new Position(row, col);
          if (this.isLegalMove(board, testPosition)) {
            list.add(testPosition);
          }
        }        
      }
    }
    return list;
  }
  
    /**
   * Get the best moves for this player on the board
   * @param board current board
   * @param legalMoves current legal moves for this player
   * @return Position that is the best move
   */
  public Position getBestMove(Board board, ArrayList<Position> legalMoves) {
    Position bestMove = legalMoves.get(0);
      //Prefer Corners
      for (int i=0; i<legalMoves.size(); i++)  {   
          if (isCorner(legalMoves.get(i))){
              return legalMoves.get(i);
          }
      }
      
      //Prefer Edges And Dont give up the corners
      for (int i=0; i<legalMoves.size(); i++)  {             
          if (isCornerAdjacent(board, legalMoves.get(i)) == false && isEdge(legalMoves.get(i))){
              return legalMoves.get(i);
          }
      }
      
      // Dont give up the corners
      for (int i=0; i<legalMoves.size(); i++)  {             
          if (isCornerAdjacent(board, legalMoves.get(i)) == false){
              return legalMoves.get(i);
          }
      }

    return bestMove;
  }
  
   /**
   * Is the Position a corner
   * @param move
   * @return True if this is a corner move for the player
   */
    public boolean isCorner (Position move) {
    if (move.getCol() == 0 && move.getRow() == 0) {     
        return true;
    }
    if (move.getCol() == 0 && move.getRow() == 7) {     
        return true;
    }
    if (move.getCol() == 7 && move.getRow() == 0) {     
        return true;
    }
    if (move.getCol() == 7 && move.getRow() == 7) {     
        return true;
    }
    return false;
  }
  
   /**
   * Is the Position next to the corner that is not taken
   * @param move
   * @return True if this is a move next to the corner for the player
   */
  public boolean isCornerAdjacent(Board board, Position move) {
    //TOP LEFT
    if (board.getSquare(this, 0, 0).getStatus() == Constants.EMPTY) {
        if (move.getCol() == 0  &&  move.getRow() == 1) {     
            return true;
        }
        if (move.getCol() == 1  &&  move.getRow() == 0) {     
            return true;
        }
        if (move.getCol() == 1  &&  move.getRow() == 1) {     
            return true;
        }
    }
    
    // TOP RIGHT
    if (board.getSquare(this, 0, 7).getStatus() == Constants.EMPTY) {
        if (move.getCol() == 7  &&  move.getRow() == 1) {     
            return true;
        }
        if (move.getCol() == 6  &&  move.getRow() == 0) {     
            return true;
        }
        if (move.getCol() == 6  &&  move.getRow() == 1) {     
            return true;
        }  
    }
        
    // BOTTOM RIGHT
    if (board.getSquare(this, 7, 7).getStatus() == Constants.EMPTY) {
        if (move.getCol() == 7  &&  move.getRow() == 6) {     
            return true;
        }
        if (move.getCol() == 6  &&  move.getRow() == 7) {     
            return true;
        }
        if (move.getCol() == 6  &&  move.getRow() == 6) {     
            return true;
        }
    }
    
    // BOTTOM LEFT
    if (board.getSquare(this, 7, 0).getStatus() == Constants.EMPTY) {
        if (move.getCol() == 7  &&  move.getRow() == 1) {     
            return true;
        }
        if (move.getCol() == 6  &&  move.getRow() == 0) {     
            return true;
        }
        if (move.getCol() == 6  &&  move.getRow() == 1) {     
            return true;
        }
    }

    return false;
  }    
    
   /**
   * Is the Position an edge
   * @param move
   * @return True if this is an edge move for the player
   */
  public boolean isEdge(Position move) {
    if (move.getCol() == 0 || move.getCol() == 7 || move.getRow() == 0 || move.getRow() == 7) {     
        return true;
    }
    return false;
  }
  
}