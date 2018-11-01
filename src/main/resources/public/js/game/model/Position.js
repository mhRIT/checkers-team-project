/**
 * This module exports the Position class constructor.
 * 
 * This component is an Information Expert on checkers board positions.
 */
define(function(require){
  'use strict';
  
  /**
   * Constructor function.
   */
  function Position(row, cell) {
    // validate
    if (typeof row !== 'number') {
      alert('error: position');
      throw new Error('row (' + row + ') is not a number.');
    }
    if (typeof cell !== 'number') {
      alert('error: position');
      throw new Error('cell (' + cell + ') is not a number.');
    }
    //
    this.row = row;
    this.cell = cell;
  };

  // export class constructor
  return Position;
  
});
