/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {

        'devojka_na_maturi_bordo': '#631320',
  
        'karmin_crvena': '#AB1014',
  
        'svetloplava': '#0E5398',
  
        'dabudi_dabada': '#053B6A',
  
        'pozadina': '#053B6A',

        'teal' : '#12a392',
  
      },

      screens: {
        'phone':'320px',
        'pc': '767px'
      },
    },
    
  },
  plugins: [
    require('@tailwindcss/line-clamp')
  ],
  variants: {
    extend: {
        display: ["group-hover"],
    },
},
}
