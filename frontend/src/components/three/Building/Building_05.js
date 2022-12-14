/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
*/

import React, { useRef } from 'react'
import { useGLTF } from '@react-three/drei'

function Building_05(props) {
  const { nodes, materials } = useGLTF('/Building/Building_05.glb')
  return (
    <group {...props} dispose={null}>
      <mesh geometry={nodes.House_2.geometry} material={materials.Mat} />
    </group>
  )
}

useGLTF.preload('/Building/Building_05.glb')
export default Building_05
