#version 120

attribute vec3 blockPosition;
attribute vec3 blockColor;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

varying vec3 varColor;

void main() {
    gl_Position = projectionMatrix * viewMatrix * vec4(blockPosition, 1);
    varColor = blockColor;
}