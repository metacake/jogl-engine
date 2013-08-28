#version 150
in vec4 position;
in vec4 color;

out vec4 inColor;

uniform mat4 perspectiveMatrix;

void main() {
    vec4 cameraPos = position + vec4(0.5f, 0.25f, 0, 0);
    gl_Position = perspectiveMatrix * cameraPos;
    inColor = color;
}