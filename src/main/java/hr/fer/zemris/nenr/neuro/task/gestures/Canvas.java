package hr.fer.zemris.nenr.neuro.task.gestures;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    private List<Point> points = new ArrayList<>();

    public Canvas() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void reset() {
        points = new ArrayList<>();
        repaint();
    }

    public List<Point> getPoints() {
        return points;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        points.add(new Point(e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        points.add(new Point(e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        points.add(new Point(e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (points.size() > 1) {
            Point last = points.get(0);
            for (int i = 1; i < points.size(); ++i) {
                Point cur = points.get(i);
                g.drawLine(last.x, last.y, cur.x, cur.y);
                last = cur;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
