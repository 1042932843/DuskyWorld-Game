package com.dusky.game.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CalculationUtil {
    public static float angleBetweenTwoPoints(Vector2 A,Vector2 B) {
        float angle = MathUtils.atan2(A.y - B.y, A.x - B.x);
        angle %= 2 * MathUtils.PI;
        if (angle < 0) angle += 2 * MathUtils.PI2;
        return angle;
    }

    public static float distanceBetweenTwoPoints(Vector2 A,Vector2 B) {
        return (float) Math.sqrt(((A.x - B.x) * (A.x - B.x)) + ((A.y -
                B.y) * (A.y - B.y)));
    }

    public void calculateAngleAndDistanceForBullet(Viewport viewport,Vector2 A, Vector2 B, int screenX, int screenY, float distance, float angle) {
        B.set(screenX, screenY);
        viewport.unproject(B);
        if (distance > 100) {
            distance = 100;
        }

        B.set(A.x + (distance * -MathUtils.cos(angle)), A.y + (distance * -MathUtils.sin(angle)));
    }
}
