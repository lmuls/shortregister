import React, { PureComponent } from "react";
import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";
import { ShortPositionHistoryDto } from "../pages/instrumenter";
import { parse, parseShortPositions } from "../util/mainUtils";

const data = [
  {
    date: "01.01.2021",
    uv: 4000,
    pv: 2400,
    amt: 2400,
  },
  {
    date: "02.01.2021",
    uv: 3000,
    pv: 1398,
    amt: 2210,
  },
  {
    date: "03.01.2021",
    uv: 2000,
    pv: 9800,
    amt: 2290,
  },
];

export default class StackedAreaChart extends PureComponent<{ data: [] }> {
  constructor(props) {
    super(props);
    const chartData = parseShortPositions(this.props.data);
    const chartData2 = parse(this.props.data);

    const entries = this.props.data.map((shortPosition) => {
      return shortPosition.companyName;
    });

    // this.setState({chartData: chartData})
    this.state = { chartData: chartData2, entries: entries };
    console.log(this.props.data);
  }

  componentDidMount() {
    // console.log(Object.entries(this.props.data[0]))
    // console.log(this.state.entries)
  }

  render() {
    return (
      <ResponsiveContainer width="100%" height="100%">
        <AreaChart
          width={500}
          height={400}
          data={data}
          margin={{
            top: 10,
            right: 30,
            left: 0,
            bottom: 0,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="date" />
          <YAxis />
          <Tooltip />
          {/*{this.state.entries.map((entry) => {*/}
          {/*  return (*/}
          {/*    <Area*/}
          {/*      type="monotone"*/}
          {/*      dataKey={entry}*/}
          {/*      stackId="1"*/}
          {/*      stroke="#8884d8"*/}
          {/*      fill="#8884d8"*/}
          {/*    />*/}
          {/*  );*/}
          {/*})}*/}
          <Area
            type="monotone"
            dataKey="pv"
            stackId="1"
            stroke="#82ca9d"
            fill="#82ca9d"
          />
          <Area
            type="monotone"
            dataKey="amt"
            stackId="1"
            stroke="#ffc658"
            fill="#ffc658"
          />
        </AreaChart>
      </ResponsiveContainer>
    );
  }
}
