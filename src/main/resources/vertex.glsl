#version 150
in vec4 position;
in vec4 color;

out vec4 inColor;

uniform mat4 modelToWorld;
uniform mat4 worldToCamera;
uniform mat4 cameraToClip;

void main() {
    vec4 temp = modelToWorld * position;
    temp = worldToCamera * temp;
    gl_Position = cameraToClip * temp;
    inColor = color;
}