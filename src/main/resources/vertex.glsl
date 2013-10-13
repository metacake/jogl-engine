#version 150
in vec4 position;
in vec4 color;

out vec4 inColor;

uniform mat4 modelToCamera;
uniform mat4 cameraToClip;

void main() {
    vec4 cameraPos = modelToCamera * position;
    gl_Position = cameraToClip * cameraPos;
    inColor = color;
}