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

interface ChartDataDto {
  [key: string]: string;
}

interface ChartProps {
  subject: string;
}

interface ChartState {
  subject: string;
  chartData: ChartDataDto[];
  entries: string[];
}

export default class StackedAreaChart extends React.Component<
  ChartProps,
  ChartState
> {
  state: ChartState = {
    subject: this.props.subject,
    chartData: [],
    entries: [],
  };

  async componentDidMount() {
    const res = await fetch(process.env.NEXT_PUBLIC_API_URL + "chart-data", {
      method: "POST",
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify({
        subject: this.props.subject,
      }),
    });
    const chartData: ChartDataDto[] = await res.json();
    this.setState((state) => ({ chartData: chartData }));

    const entries: string[] = [];
    chartData.forEach((inst) => {
      for (const entry of Object.getOwnPropertyNames(inst)) {
        if (entry !== "date" && !entries.includes(entry)) {
          entries.push(entry);
        }
      }
    });
    this.setState((state) => ({ entries: entries }));
  }

  render() {
    return (
      <ResponsiveContainer width="100%" height="100%">
        <AreaChart
          width={500}
          height={400}
          data={this.state.chartData}
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
          {this.state.entries.map((entry) => {
            return (
              <Area
                key={entry}
                type="monotone"
                dataKey={entry}
                stackId="1"
                stroke="#8884d8"
                fill="#8884d8"
              />
            );
          })}
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
