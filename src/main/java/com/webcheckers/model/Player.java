<<<<<<< HEAD
package com.webcheckers.model;

public class Player {

  /**
   * Player.java
   * Represents a single user
   */
  private String name;

  public Player(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
||||||| merged common ancestors
=======
package com.webcheckers.model;

/* Player.java
 * Represents a single user
 */

public class Player {
    private String name;
    private Boolean inGame;



    public Player(String name){
        this.name = name;
        this.inGame = false;
    }

    public String getName(){
        return name;
    }


}
>>>>>>> PlayerSignInStory
