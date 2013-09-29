#version 150
in vec4 position;
in vec4 color;

uniform vec3 offset;

out vec4 inColor;

uniform mat4 perspectiveMatrix;

void main() {
    vec4 cameraPos = position + vec4(offset, 0);
    gl_Position = perspectiveMatrix * cameraPos;
    inColor = color;
}