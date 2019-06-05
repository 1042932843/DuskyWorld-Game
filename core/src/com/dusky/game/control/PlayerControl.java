package com.dusky.game.control;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dusky.game.objects.Player;
import com.dusky.game.utils.CalculationUtil;


public class PlayerControl extends InputAdapter {
    Player player;
    Viewport viewport;
    private  Vector2 firingPosition;

    public PlayerControl(Player player,Viewport viewport) {
        this.player=player;
        this.viewport=viewport;
        firingPosition= player.getBody().getPosition().cpy();

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        firingPosition.set(screenX, screenY);
        viewport.unproject(firingPosition);
        player.createBullet(firingPosition);
        firingPosition.set(player.getBody().getPosition().cpy());
        return true;
    }
}
