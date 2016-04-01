# ICS4U-Spencer

public void setup() {
  size(600, 600);
  background(0);
  stroke(255);
  line(width / 2, height, width / 2, height / 2);
  drawTree(width / 2, height / 2, height / 4);
}

public void drawTree(int x1, int y1, int length) {
  System.out.println(x1);
  System.out.println(length);
  line(x1, y1, x1 + length * (int) cos(0.785398), y1 + length * (int) sin(0.785398));
  if (length > 1) {
   drawTree(x1 + length * (int) cos(0.785398), y1 + length * (int) sin(0.785398), length / 2);
  }
}
