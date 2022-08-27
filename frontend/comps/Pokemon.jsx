const data = {
  overskrift: "Charizard",
  hp: 120,
  url: "https://assets.pokemon.com/assets/cms2/img/pokedex/full/006.png",
  attacks: ["Fire Spin", "Energy Burn"],
};

function Pokemon({ data }) {
  return (
    <div className={"card"}>
      <div className={"header"}>
        <h4>{data.overskrift}</h4>
        <p>HP: {data.hp}</p>
      </div>
      <img src={data.url} alt="" />
      <div className="info">
        {data.attacks.map((attack) => {
          return <p key={attack}>{attack}</p>;
        })}
      </div>
    </div>
  );
}

export default () => {
  return Pokemon({ data });
};
